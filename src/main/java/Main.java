import common.Args;
import entity.Addr;
import entity.Converter;
import entity.HierarchyItem;
import lombok.extern.slf4j.Slf4j;
import models.AddrObject;
import models.AdmHierarchy;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static common.FileUtils.*;

@Slf4j
public class Main {
	private static XmlMapper xml = new XmlMapper();

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
				Converter.addrs(objects).stream()
						.filter(o -> o.startDate().isBefore(date))
						.filter(o -> o.endDate().isAfter(date))
						.filter(o -> objectIds.contains(o.id()))
						// без фильтрации isActual и isActive, т.к. не указано
						.forEach(o -> System.out.printf("%d: %s %s%n", o.id(), o.typeName(), o.name()));
			}
			case 2 -> log.info("Task 2 not implemented");
		}
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
