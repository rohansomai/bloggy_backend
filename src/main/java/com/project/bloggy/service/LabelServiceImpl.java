package com.project.bloggy.service;

import com.project.bloggy.dto.LabelDTO;
import com.project.bloggy.entity.Label;
import com.project.bloggy.exception.ResourceNotFoundException;
import com.project.bloggy.repository.LabelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {

    public LabelServiceImpl(LabelRepository labelRepository, ModelMapper modelMapper) {
        this.labelRepository = labelRepository;
        this.modelMapper = modelMapper;
    }

    private final LabelRepository labelRepository;

    private final ModelMapper modelMapper;

    @Override
    public LabelDTO createLabel(LabelDTO labelDTO) {
        Label savedLabel = this.labelRepository.save(dtoToLabel(labelDTO));
        return labelToDTO(this.labelRepository.save(savedLabel));
    }

    @Override
    public LabelDTO updateLabel(LabelDTO labelDTO, Long labelId) {
        Label currentLabel = this.labelRepository.findById(labelId).orElseThrow(() ->
                new ResourceNotFoundException("Label", "labelId", labelId));
        currentLabel.setTitle(labelDTO.getDescription());
        currentLabel.setDescription(labelDTO.getDescription());
        return labelToDTO(this.labelRepository.save(currentLabel));
    }

    @Override
    public LabelDTO getLabelById(Long labelId) {
        return labelToDTO(this.labelRepository.findById(labelId).orElseThrow(() ->
                new ResourceNotFoundException("Label", "labelId", labelId)));
    }

    @Override
    public List<LabelDTO> getAllLabels() {
        List<Label> labelList = this.labelRepository.findAll();
        return labelList.stream().map((this::labelToDTO)).collect(Collectors.toList());
    }

    @Override
    public void deleteLabel(Long labelId) {
        Label label = this.labelRepository.findById(labelId).orElseThrow(() ->
                new ResourceNotFoundException("Label", "labelId", labelId));
        this.labelRepository.delete(label);
    }

    private Label dtoToLabel(LabelDTO labelDTO) {
        return this.modelMapper.map(labelDTO, Label.class);
    }

    private LabelDTO labelToDTO(Label label) {
        return this.modelMapper.map(label, LabelDTO.class);
    }
}
