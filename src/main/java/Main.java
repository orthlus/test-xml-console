import common.Args;
import entity.Addr;
import entity.HierarchyItem;
import lombok.extern.slf4j.Slf4j;
import models.AddrObject;
import models.AdmHierarchy;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static common.FileUtils.*;
import static entity.Converter.addrs;
import static entity.Converter.hierarchy;

@Slf4j
public class Main {
	public static void main(String[] args) {
		Args.init(args);
		Args params = Args.getInstance();
		main0(params);
	}

	private static void main0(Args args) {
		var lists = readFiles(args);
		List<AddrObject> objects = lists.v1;
		List<AdmHierarchy> hierarchy = lists.v2;

		if (args.taskId().isEmpty()) {
			log.error("Wrong task number, allow 1 and 2");
			throw new RuntimeException();
		}

		switch (args.taskId().get()) {
			case 1 -> {
				LocalDate date = args.getDate();
				Set<Integer> objectIds = args.getObjectIds();
				addrs(objects).stream()
						.filter(o -> o.startDate().isBefore(date))
						.filter(o -> o.endDate().isAfter(date))
						.filter(o -> objectIds.contains(o.id()))
						// без фильтрации isActual и isActive, т.к. не указано
						.forEach(o -> System.out.printf("%d: %s %s%n", o.id(), o.typeName(), o.name()));
			}
			case 2 -> {
				String taskPattern = "проезд";

				Set<Addr> addrs = addrs(objects).stream()
						.filter(Addr::isActive)
						.filter(Addr::isActual)
						.collect(Collectors.toSet());
				Set<Integer> childrenIds = addrs.stream()
						.filter(o -> o.typeName().equals(taskPattern))
						.map(Addr::id)
						.collect(Collectors.toSet());
				Map<Integer, Addr> addrsMap = addrs.stream()
						.collect(Collectors.toMap(Addr::id, a -> a, (a, b) -> b));

				Map<Integer, HierarchyItem> hierarchyMap = hierarchy(hierarchy).stream()
						.filter(HierarchyItem::isActive)
						.collect(Collectors.toMap(HierarchyItem::id, a -> a, (a, b) -> b));

				hierarchyMap.values().stream()
						.filter(child -> childrenIds.contains(child.id()))
						.forEach(child -> printChain(addrsMap, getParents(child, 3, hierarchyMap)));
			}
		}
	}

	private static List<HierarchyItem> getParents(HierarchyItem item, int deep, Map<Integer, HierarchyItem> hierarchyMap) {
		Set<HierarchyItem> res = new LinkedHashSet<>();
		res.add(item);
		HierarchyItem last = item;
		for (int i = 0; i < deep; i++) {
			HierarchyItem parent = getParent(last, hierarchyMap);
			res.add(parent);
			last = parent;
		}
		// развернуть set
		ArrayList<HierarchyItem> arr = new ArrayList<>(res);
		Collections.reverse(arr);
		return arr;
	}

	private static void printChain(Map<Integer, Addr> addrsMap, Collection<HierarchyItem> chain) {
		String s = chain.stream()
				.filter(Objects::nonNull)
				.map(i -> getName(i, addrsMap))
				.collect(Collectors.joining(", "));
		System.out.println(s);
	}

	private static void printChain(Map<Integer, Addr> addrsMap, HierarchyItem... chain) {
		String s = Arrays.stream(chain)
				.filter(Objects::nonNull)
				.map(i -> getName(i, addrsMap))
				.collect(Collectors.joining(", "));
		System.out.println(s);
	}

	private static String getName(HierarchyItem item, Map<Integer, Addr> addrsMap) {
		Addr addr = addrsMap.get(item.id());
		return addr.typeName() + " " + addr.name();
	}

	private static HierarchyItem getParent(HierarchyItem item, Map<Integer, HierarchyItem> hierarchy) {
		return item.parentId() == 0 ? null : hierarchy.get(item.parentId());
	}

	private static Tuple2<List<AddrObject>, List<AdmHierarchy>> readFiles(Args args) {
		Optional<Tuple2<Path, Path>> files = searchFilesInArgs(args);
		if (files.isEmpty()){
			files = searchFilesInCurrentDir();

			if (files.isEmpty()) {
				log.error("Files not found");
				System.exit(1);
			}
		}

		return Tuple.tuple(readObjects(files.get().v1), readHierarchy(files.get().v2));
	}
}
