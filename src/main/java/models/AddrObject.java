package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "OBJECT")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
public class AddrObject {
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

	@Override
	public String toString() {
		return "AddrObject{id='%s', objectId='%s', objectGUID='%s', changeId='%s', name='%s', typename='%s', level='%s', operTypeId='%s', prevId='%s', nextId='%s', updateDate='%s', startDate='%s', endDate='%s', isActual='%s', isActive='%s'}"
				.formatted(id, objectId, objectGUID, changeId, name, typename, level, operTypeId, prevId, nextId, updateDate, startDate, endDate, isActual, isActive);
	}
}
