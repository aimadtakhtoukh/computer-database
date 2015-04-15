package com.excilys.mappers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.beans.Computer;
import com.excilys.dao.CompanyDAO;
import com.excilys.servlet.dto.ComputerDTO;

@Component
public class ComputerDTOMapper {

	final Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);
	
	@Autowired
	private CompanyDAO companyDAO;
	
	private static DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	
	public ComputerDTO toComputerDTO(Computer bean) {
		if (bean == null) {
			return null;
		}
		ComputerDTO dto = new ComputerDTO();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		if (bean.getIntroduced() != null) {
			dto.setIntroduced(bean.getIntroduced().toLocalDate().toString());
		}
		if (bean.getDiscontinued() != null) {
			dto.setDiscontinued(bean.getDiscontinued().toLocalDate().toString());
		}
		if (bean.getCompany() != null) {
			dto.setCompanyId(bean.getCompany().getId());
			dto.setCompanyName(bean.getCompany().getName());
		} else {
			dto.setCompanyId(null);
			dto.setCompanyName(null);
		}
		return dto;
	}
	
	public Computer toComputer(ComputerDTO dto) {
		if (dto == null) {
			return null;
		}
		Computer bean = new Computer();
		bean.setId(dto.getId());
		bean.setName(dto.getName());
		if (dto.getIntroduced() != null) {
			try {
				bean.setIntroduced(LocalDateTime.ofInstant(sdf.parse(dto.getIntroduced()).toInstant(), ZoneId.systemDefault()));
			} catch (ParseException e) {
				bean.setIntroduced(null);
			}
		} else {
			bean.setIntroduced(null);
		}
		if (dto.getDiscontinued() != null) {
			try {
				bean.setDiscontinued(LocalDateTime.ofInstant(sdf.parse(dto.getDiscontinued()).toInstant(), ZoneId.systemDefault()));
			} catch (ParseException e) {
				bean.setDiscontinued(null);
			}
		} else {
			bean.setDiscontinued(null);
		}
		if (dto.getCompanyId() != null) {
			bean.setCompany(companyDAO.get(dto.getCompanyId()));
		} else {
			bean.setCompany(null);
		}
		return bean;
	}

}
