package entity;

import models.AddrObject;
import models.AdmHierarchy;

import java.time.LocalDate;
import java.util.List;

public class Converter {
	public static List<HierarchyItem> hierarchy(List<AdmHierarchy> objects) {
		return objects.stream().map(Converter::hierarchyItem).toList();
	}

	public static HierarchyItem hierarchyItem(AdmHierarchy object) {
		return new HierarchyItem(
				Integer.parseInt(object.getObjectId()),
				Integer.parseInt(object.getParentObjId()),
				parseBool(object.getIsActive())
		);
	}

	public static List<Addr> addrs(List<AddrObject> objects) {
		return objects.stream().map(Converter::addr).toList();
	}

	public static Addr addr(AddrObject object) {
		return new Addr(
				Integer.parseInt(object.getObjectId()),
				object.getName(),
				object.getTypeName(),
				LocalDate.parse(object.getStartDate()),
				LocalDate.parse(object.getEndDate()),
				parseBool(object.getIsActual()),
				parseBool(object.getIsActive())
		);
	}

	private static boolean parseBool(String value) {
		return value.equals("1");
	}
}
