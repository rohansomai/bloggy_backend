package com.project.bloggy.service;

import com.project.bloggy.dto.LabelDTO;

import java.util.List;

public interface LabelService {

    LabelDTO createLabel(LabelDTO labelDTO);

    LabelDTO updateLabel(LabelDTO labelDTO, Long labelId);

    LabelDTO getLabelById(Long labelId);

    List<LabelDTO> getAllLabels();

    void deleteLabel(Long labelId);
}
