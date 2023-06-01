package com.aguri.captionlive.controller;


import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Remark;
import com.aguri.captionlive.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/remarks")
public class RemarkController {
    @Autowired
    private RemarkService remarkService;

    @PutMapping("/{remarkId}")
    public ResponseEntity<Resp> updateRemark(@PathVariable Long remarkId, @RequestBody Remark remark) {
        return ResponseEntity.ok(Resp.ok(remarkService.updateRemark(remarkId, remark)));
    }

    @PostMapping
    public ResponseEntity<Resp> updateRemark(@RequestBody Remark remark) {
        return ResponseEntity.ok(Resp.ok(remarkService.createRemark(remark)));
    }

    @DeleteMapping("/{remarkId}")
    public ResponseEntity<Void> deleteRemark(@PathVariable Long remarkId) {
        remarkService.getRemarkById(remarkId);
        remarkService.deleteRemark(remarkId);
        return ResponseEntity.noContent().build();
    }
}
