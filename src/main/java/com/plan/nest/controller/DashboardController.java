package com.plan.nest.controller;

import com.plan.nest.entity.Board;
import com.plan.nest.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashBoardService dashBoardService;

    @GetMapping
    public List<Board> getBoards(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size
    ) {
        return dashBoardService.getBoards(page, size);
    }

}
