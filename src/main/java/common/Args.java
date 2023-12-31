package common;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Slf4j
public class Args {
	private static Args instance;

	private Args() {
	}

	public static void init(String[] args) {
		Args instance = getInstance();
		try {
			JCommander jCommander = JCommander.newBuilder().addObject(instance).build();
			jCommander.parse(args);
			if (instance.help) {
				log.info("Была вызвана справка использования консоли.");
				jCommander.usage();
				System.exit(0);
			}
		} catch (ParameterException e) {
			e.printStackTrace();
			e.usage();
			System.exit(1);
		}
	}

	public static Args getInstance() {
		if (instance == null) {
			instance = new Args();
		}
		return instance;
	}

	@Parameter(names = "-help", help = true)
	private boolean help = false;

	@Parameter(names = {"-task", "-t"}, required = true, description = "task number for run")
	private int taskId;
	@Parameter(names = {"-objects-file", "-obj"}, description = "path to xml object file. by default file 'AS_ADDR_OBJ.XML' looking in current directory")
	private String objectsFilePath;
	@Parameter(names = {"-hierarchy", "-hierarchy-file"}, description = "path to xml hierarchy file. by default file 'AS_ADM_HIERARCHY.XML' looking in current directory")
	private String hierarchyFilePath;
	@Parameter(names = {"-date", "-d"}, description = "required for task 1. date format yyyy-MM-dd")
	private String date;
	@Parameter(names = {"-objects", "-o"}, variableArity = true, description = "required for task 1. list number with comma or space delimiter")
	private List<Integer> objectIds;

	public Optional<String> getObjectsFile() {
		return objectsFilePath == null ? empty() : of(objectsFilePath);
	}

	public Optional<String> getHierarchyFile() {
		return hierarchyFilePath == null ? empty() : of(hierarchyFilePath);
	}

	public Optional<Integer> taskId() {
		return taskId == 1 || taskId == 2 ? of(taskId) : empty();
	}

	public LocalDate getDate() {
		return LocalDate.parse(date);
	}

	public Set<Integer> getObjectIds() {
		return new HashSet<>(objectIds);
	}
}
