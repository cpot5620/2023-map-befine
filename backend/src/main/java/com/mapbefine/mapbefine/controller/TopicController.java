package com.mapbefine.mapbefine.controller;

import com.mapbefine.mapbefine.dto.TopicCreateRequest;
import com.mapbefine.mapbefine.dto.TopicDetailResponse;
import com.mapbefine.mapbefine.dto.TopicMergeRequest;
import com.mapbefine.mapbefine.dto.TopicResponse;
import com.mapbefine.mapbefine.dto.TopicUpdateRequest;
import com.mapbefine.mapbefine.service.TopicCommandService;
import com.mapbefine.mapbefine.service.TopicQueryService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicCommandService topicCommandService;
    private final TopicQueryService topicQueryService;

    public TopicController(TopicCommandService topicCommandService, TopicQueryService topicQueryService) {
        this.topicCommandService = topicCommandService;
        this.topicQueryService = topicQueryService;
    }

    @PostMapping("/new")
    public ResponseEntity<Void> create(@RequestBody TopicCreateRequest topicCreateRequest) {
        long topicId = topicCommandService.createNew(topicCreateRequest);

        return ResponseEntity.created(URI.create("/topics/" + topicId))
                .build();
    }

    @PostMapping("/merge")
    public ResponseEntity<Void> mergeAndCreate(@RequestBody TopicMergeRequest topicMergeRequest) {
        long topicId = topicCommandService.createMerge(topicMergeRequest);

        return ResponseEntity.created(URI.create("/topics/" + topicId))
                .build();
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<Void> update(@PathVariable Long topicId, @RequestBody TopicUpdateRequest topicUpdateRequest) {
        topicCommandService.update(topicId, topicUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> delete(@PathVariable Long topicId) {
        topicCommandService.delete(topicId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> findAll() {
        List<TopicResponse> topics = topicQueryService.findAll();

        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailResponse> findById(@PathVariable Long id) {
        TopicDetailResponse response = topicQueryService.findById(id);

        return ResponseEntity.ok(response);
    }
}