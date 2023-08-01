package models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

import java.util.List;

@JacksonXmlRootElement(localName = "ADDRESSOBJECTS")
public class AddrObjectList {
	@Getter
	@JacksonXmlProperty(localName = "OBJECT")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<AddrObject> objects;
}
