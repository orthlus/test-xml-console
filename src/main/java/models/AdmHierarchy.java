package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "ITEM")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
public class AdmHierarchy {
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

	@Override
	public String toString() {
		return "AdmHierarchy{id='%s', objectId='%s', parentObjId='%s', changeId='%s', prevId='%s', nextId='%s', updateDate='%s', startDate='%s', endDate='%s', isActive='%s'}"
				.formatted(id, objectId, parentObjId, changeId, prevId, nextId, updateDate, startDate, endDate, isActive);
	}
}
