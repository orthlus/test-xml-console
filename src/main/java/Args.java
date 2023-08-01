import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class Args {
	private static Args instance;

	private Args() {
	}

	static void init(String[] args) {
		Args instance = getInstance();
		try {
			JCommander jCommander = JCommander.newBuilder().addObject(instance).build();
			jCommander.parse(args);
			if (instance.help) {
				log.info("Была вызвана справка использования консоли.");
				jCommander.usage();
				throw new RuntimeException();
			}
		} catch (ParameterException e) {
			e.printStackTrace();
			e.usage();
			throw new RuntimeException("Не валидные параметры запуска.");
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

	@Parameter(names = "-task", required = true, description = "task number for run")
	private int taskId;
	@Parameter(names = "-objects-file", description = "path to xml object file. by default file 'AS_ADDR_OBJ.XML' looking in current directory")
	private String objectsFilePath;
	@Parameter(names = "-hierarchy-file", description = "path to xml hierarchy file. by default file 'AS_ADM_HIERARCHY.XML' looking in current directory")
	private String hierarchyFilePath;
	@Parameter(names = "-date", description = "required for task 1. date format yyyy-MM-dd")
	private LocalDate date;
	@Parameter(names = {"-objects", "-o"}, variableArity = true, description = "required for task 1. list number with comma delimiter")
	private List<Integer> objectIds;
}