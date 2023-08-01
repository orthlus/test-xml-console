package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "ITEMS")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Hierarchy {
	@JacksonXmlProperty(localName = "ID")
	private String id;
	@JacksonXmlProperty(localName = "OBJECTID")
	private String objectId;
	@JacksonXmlProperty(localName = "PARENTOBJID")
	private String parentObjId;
	@JacksonXmlProperty(localName = "CHANGEID")
	private String changeId;
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
	@JacksonXmlProperty(localName = "ISACTIVE")
	private String isActive;
}
