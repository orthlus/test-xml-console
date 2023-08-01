package models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

import java.util.List;

@JacksonXmlRootElement(localName = "ITEMS")
public class AdmHierarchyList {
	@Getter
	@JacksonXmlProperty(localName = "ITEM")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<AdmHierarchy> elements;
}
