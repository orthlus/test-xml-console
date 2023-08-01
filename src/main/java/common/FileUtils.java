package common;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import models.AddrObject;
import models.AddrObjectList;
import models.AdmHierarchy;
import models.AdmHierarchyList;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileUtils {
	private static XmlMapper xml = new XmlMapper();

	public static Optional<Tuple2<Path, Path>> searchFilesInArgs(Args args) {
		if (args.getObjectsFile().isPresent() && args.getHierarchyFile().isPresent()) {
			return Optional.of(Tuple.tuple(
					Path.of(args.getObjectsFile().get()),
					Path.of(args.getHierarchyFile().get())
			));
		}
		return Optional.empty();
	}

	public static Optional<Tuple2<Path, Path>> searchFilesInCurrentDir() {
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

	public static List<AddrObject> readObjects(Path file) {
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

	public static List<AdmHierarchy> readHierarchy(Path file) {
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
