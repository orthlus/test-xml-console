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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

	private static Optional<Tuple2<Path, Path>> searchFilesInArgs(Args args) {
		if (args.getObjectsFile().isPresent() && args.getHierarchyFile().isPresent()) {
			return Optional.of(Tuple.tuple(
					Path.of(args.getObjectsFile().get()),
					Path.of(args.getHierarchyFile().get())
			));
		}
		return Optional.empty();
	}

	private static Optional<Tuple2<Path, Path>> searchFilesInCurrentDir() {
		Path currentDir = Path.of(System.getProperty("user.dir"));
		try (Stream<Path> walk = Files.walk(currentDir)) {
			Set<Path> xmlFiles = walk
					.filter(file -> file.getFileName().toString().toLowerCase().endsWith(".xml"))
					.collect(Collectors.toSet());
			Path objectsFile = null;
			Path hierarchyFile = null;
			for (Path file : xmlFiles) {
				String fileName = file.getFileName().toString();
				if (fileName.equals("AS_ADDR_OBJ.XML")) objectsFile = file;
				if (fileName.equals("AS_ADM_HIERARCHY.XML")) hierarchyFile = file;
			}
			if (objectsFile == null || hierarchyFile == null) {
				log.error("Files not found in current directory");
				System.exit(1);
			}

			return Optional.of(Tuple.tuple(objectsFile, hierarchyFile));
		} catch (IOException e) {
			log.error("Error looking files in current directory", e);
			return Optional.empty();
		}
	}

	private static List<AddrObject> readObjects(Path file) {
		try {
			if (Files.notExists(file)) {
				log.error("File {} not found", file);
				System.exit(1);
			}
			return xml.readValue(Files.readString(file), AddrObjectList.class).getObjects();
		} catch (IOException e) {
			log.error("Error reading objects file {}", file, e);
			return List.of();
		}
	}

	private static List<AdmHierarchy> readHierarchy(Path file) {
		try {
			if (Files.notExists(file)) {
				log.error("File {} not found", file);
				System.exit(1);
			}
			return xml.readValue(Files.readString(file), AdmHierarchyList.class).getElements();
		} catch (IOException e) {
			log.error("Error reading hierarchy file {}", file, e);
			return List.of();
		}
	}
}
