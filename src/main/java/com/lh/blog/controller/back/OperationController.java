package com.lh.blog.controller.back;

import com.lh.blog.bean.Operation;
import com.lh.blog.service.OperationService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OperationController {
    @Autowired
    OperationService operationService;

    @GetMapping(value = "/admin/operations")
    public PageUtil<Operation> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        PageUtil<Operation> page = operationService.list(start, size, 5);
        return page;
    }

    @PostMapping(value = "/admin/operations/search")
    public List<Operation> search(@RequestParam(value = "key") String key) throws Exception {
        List<Operation> users = operationService.listByKey(key);
        return users;
    }

    @PostMapping(value = "/admin/operations")
    public void add(@RequestBody Operation operation) throws Exception
    {
        operationService.add(operation);
    }

    @DeleteMapping(value = "/admin/operations/{id}")
    public String delete(@PathVariable("id") int id) throws Exception
    {
        operationService.delete(id);
        return null;
    }

    @PutMapping(value = "/admin/operations/{id}")
    public void update(@RequestBody Operation operation) throws Exception
    {
        operationService.update(operation);
    }

    @GetMapping(value = "/admin/operations/{id}")
    public Operation get(@PathVariable("id")int id) throws Exception
    {
        return operationService.get(id);
    }

}
