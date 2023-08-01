package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "ADDRESSOBJECTS")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Object {
	@JacksonXmlProperty(localName = "ID")
	private String id;
	@JacksonXmlProperty(localName = "OBJECTID")
	private String objectId;
	@JacksonXmlProperty(localName = "OBJECTGUID")
	private String objectGUID;
	@JacksonXmlProperty(localName = "CHANGEID")
	private String changeId;
	@JacksonXmlProperty(localName = "NAME")
	private String name;
	@JacksonXmlProperty(localName = "TYPENAME")
	private String typename;
	@JacksonXmlProperty(localName = "LEVEL")
	private String level;
	@JacksonXmlProperty(localName = "OPERTYPEID")
	private String operTypeId;
	@JacksonXmlProperty(localName = "PREVID")
	private String prevId;
	@JacksonXmlProperty(localName = "NEXTID")
	private String nextId;
	@JacksonXmlProperty(localName = "UPDATEDATE")
	private String updateDate;
	@JacksonXmlProperty(localName = "STARTDATE")
	private String startDate;
	@JacksonXmlProperty(localName = "ENDDATE")
	private String endDate;
	@JacksonXmlProperty(localName = "ISACTUAL")
	private String isActual;
	@JacksonXmlProperty(localName = "ISACTIVE")
	private String isActive;
}
