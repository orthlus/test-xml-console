package entity;

import models.AddrObject;

import java.time.LocalDate;
import java.util.List;

public class Converter {
	public static List<Addr> addrs(List<AddrObject> objects) {
		return objects.stream().map(Converter::addr).toList();
	}

	public static Addr addr(AddrObject object) {
		return new Addr(
				Integer.parseInt(object.getObjectId()),
				object.getName(),
				LocalDate.parse(object.getStartDate()),
				LocalDate.parse(object.getEndDate())
		);
	}
}
