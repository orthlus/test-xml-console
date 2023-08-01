import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import entity.Converter;
import lombok.extern.slf4j.Slf4j;
import models.*;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
						.forEach(o -> System.out.printf("%d %s", o.id(), o.name()));
			}
			case 2 -> log.info("Task 2 not implemented");
		}
	}

	private static Tuple2<List<AddrObject>, List<AdmHierarchy>> readFiles(Args args) {
		Path objectsFile;
		Path hierarchyFile;

		if (args.getObjectsFile().isEmpty() || args.getHierarchyFile().isEmpty()) {
			Path currentDir = Path.of(System.getProperty("user.dir"));

			try (Stream<Path> walk = Files.walk(currentDir)) {
				long countFiles = walk
						.filter(file -> {
							String fileName = file.getFileName().toString();
							return fileName.equals("AS_ADDR_OBJ.XML") || fileName.equals("AS_ADM_HIERARCHY.XML");
						})
						.count();
				if (countFiles != 2) {
					log.error("Error looking files in current directory");
					throw new RuntimeException();
				} else {
					objectsFile = Path.of("AS_ADDR_OBJ.XML");
					hierarchyFile = Path.of("AS_ADM_HIERARCHY.XML");
				}
			} catch (IOException e) {
				log.error("Error looking files in current directory", e);
				throw new RuntimeException(e);
			}
		} else {
			objectsFile = Path.of(args.getObjectsFile().get());
			hierarchyFile = Path.of(args.getHierarchyFile().get());
		}

		return Tuple.tuple(
				readObjects(objectsFile),
				readHierarchy(hierarchyFile)
		);
	}

	private static List<AddrObject> readObjects(Path file) {
		try {
			return xml.readValue(Files.readString(file), AddrObjectList.class).getObjects();
		} catch (IOException e) {
			log.error("Error reading objects file {}", file);
			return List.of();
		}
	}

	private static List<AdmHierarchy> readHierarchy(Path file) {
		try {
			return xml.readValue(Files.readString(file), AdmHierarchyList.class).getElements();
		} catch (IOException e) {
			log.error("Error reading hierarchy file {}", file);
			return List.of();
		}
	}
}
