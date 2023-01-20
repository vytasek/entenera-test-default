package com.etnetera.hr.data;

import com.etnetera.hr.enums.HypeLevelEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 30)
	private String name;
	@Column
	@NotNull
	private String version;
	@Column
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deprecationDate;
	@Column
	private HypeLevelEnum hypeLevel;

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name, String version, LocalDate deprecationDate, HypeLevelEnum hypeLevel) {
		this.name = name;
		this.version = version;
		this.deprecationDate = deprecationDate;
		this.hypeLevel = hypeLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDate getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(LocalDate deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	public HypeLevelEnum getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(HypeLevelEnum hypeLevel) {
		this.hypeLevel = hypeLevel;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id +
				", name=" + name +
				", version=" + version +
				", deprecationDate=" + deprecationDate +
				", hype=" + hypeLevel + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof JavaScriptFramework)) {
			return false;
		}

		JavaScriptFramework jsf = (JavaScriptFramework)obj;
		if (jsf.getName().equals(this.getName())
				&& jsf.getVersion().equals(this.getVersion())
				&& Optional.ofNullable(jsf.getDeprecationDate()).equals(Optional.ofNullable(this.getDeprecationDate()))
				&& Optional.ofNullable(jsf.getHypeLevel()).equals(Optional.ofNullable(this.getHypeLevel()))
		){
			return true;
		}
		return false;
	}
}
