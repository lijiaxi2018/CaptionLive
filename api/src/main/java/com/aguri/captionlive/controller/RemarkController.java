package com.aguri.captionlive.controller;


import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.service.RemarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/remarks")
public class RemarkController {
    @Autowired
    private RemarkService remarkService;

    @PutMapping("/{remarkId}")
    @Operation(summary = "Update remark", description = "Update the content of a remark")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remark updated successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    public ResponseEntity<Resp> updateRemark(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request body containing content",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class, requiredProperties = {"content"}),
                            examples = @ExampleObject(value = "{\"content\": \"Example remark content\", \"remarkId\": \"1\"}")))
            @RequestBody HashMap<String, String> body) {
        String content = body.get("content");
        Long remarkId = Long.valueOf(body.get("remarkId"));
        return ResponseEntity.ok(Resp.ok(remarkService.updateRemark(remarkId, content)));
    }

//    @PostMapping
//    public ResponseEntity<Resp> updateRemark(@RequestBody Remark remark) {
//        return ResponseEntity.ok(Resp.ok(remarkService.createRemark(remark)));
//    }

    @DeleteMapping("/{remarkId}")
    public ResponseEntity<Resp> deleteRemark(@PathVariable Long remarkId) {
        remarkService.getRemarkById(remarkId);
        remarkService.deleteRemark(remarkId);
        return ResponseEntity.ok(Resp.ok());
    }

    @PostMapping
    @Operation(summary = "Add remark", description = "Add a remark to a segment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remark added successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    public ResponseEntity<Resp> addRemark(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request body containing userId and content",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class, requiredProperties = {"userId", "content"}),
                            examples = @ExampleObject(value = "{\"segmentId\": 1, \"userId\": 1, \"content\": \"Example remark content\"}")))
            @RequestBody HashMap<String, String> body) {
        String content = body.get("content");
        Long segmentId = Long.valueOf(body.get("segmentId"));
        Long userId = Long.valueOf(body.get("userId"));
        return ResponseEntity.ok(Resp.ok(remarkService.addRemark(content, userId, segmentId)));
    }

}
