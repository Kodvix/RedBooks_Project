package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dto.SchoolClassDto;

public interface SchoolClassService {

    public SchoolClassDto addClass(SchoolClassDto school);

    public SchoolClassDto getClass(Long classId);

    public SchoolClassDto updateClass(Long classId, SchoolClassDto classDto);

    public void deleteClass(Long id);

}
