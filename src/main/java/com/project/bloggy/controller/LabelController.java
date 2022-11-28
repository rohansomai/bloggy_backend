package com.project.bloggy.controller;


import com.project.bloggy.dto.ApiResponse;
import com.project.bloggy.dto.LabelDTO;
import com.project.bloggy.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    private final LabelService labelService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createLabel(@Valid @RequestBody LabelDTO labelDTO) {
        LabelDTO createdLabel = this.labelService.createLabel(labelDTO);
        return new ResponseEntity<>(new ApiResponse("Label created successfully", createdLabel), HttpStatus.CREATED);
    }

    @PutMapping("/{labelId}")
    public ResponseEntity<ApiResponse> updateLabel(@PathVariable Long labelId, @Valid @RequestBody LabelDTO labelDTO) {
        LabelDTO updatedLabel = this.labelService.updateLabel(labelDTO, labelId);
        return new ResponseEntity<>(new ApiResponse("Label updated successfully", updatedLabel), HttpStatus.OK);
    }

    @DeleteMapping("/{labelId}")
    public ResponseEntity<ApiResponse> deleteLabel(@PathVariable Long labelId) {
        this.labelService.deleteLabel(labelId);
        return new ResponseEntity<>(new ApiResponse("Label deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping("/{labelId}")
    public ResponseEntity<ApiResponse> getLabel(@PathVariable Long labelId) {
        LabelDTO label = this.labelService.getLabelById(labelId);
        return new ResponseEntity<>(new ApiResponse("Label fetched successfully", label), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllLabelsList() {
        List<LabelDTO> labels = this.labelService.getAllLabels();
        return new ResponseEntity<>(new ApiResponse("Labels List fetched successfully", labels), HttpStatus.OK);
    }
}
