package com.example.memorybackend;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class MemoryController {
@GetMapping("/")
public String home() {
    return "Memory Backend Running Successfully!";
}
    @PostMapping("/firstfit")
    public Map<String, Object> firstFit(@RequestBody Map<String, List<Integer>> input) {
        return allocate(input, "first");
    }

    @PostMapping("/bestfit")
    public Map<String, Object> bestFit(@RequestBody Map<String, List<Integer>> input) {
        return allocate(input, "best");
    }

    @PostMapping("/worstfit")
    public Map<String, Object> worstFit(@RequestBody Map<String, List<Integer>> input) {
        return allocate(input, "worst");
    }

    private Map<String, Object> allocate(Map<String, List<Integer>> input, String type) {
        List<Integer> blocks = new ArrayList<>(input.get("blocks"));
        List<Integer> processes = input.get("processes");

        List<String> result = new ArrayList<>();

        for (int i = 0; i < processes.size(); i++) {
            int process = processes.get(i);
            int index = -1;

            for (int j = 0; j < blocks.size(); j++) {
                if (blocks.get(j) >= process) {
                    if (type.equals("first")) {
                        index = j;
                        break;
                    } else if (type.equals("best")) {
                        if (index == -1 || blocks.get(j) < blocks.get(index)) {
                            index = j;
                        }
                    } else if (type.equals("worst")) {
                        if (index == -1 || blocks.get(j) > blocks.get(index)) {
                            index = j;
                        }
                    }
                }
            }

            if (index != -1) {
                result.add("Process " + process + " allocated to Block " + blocks.get(index));
                blocks.set(index, blocks.get(index) - process);
            } else {
                result.add("Process " + process + " not allocated");
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        response.put("remainingBlocks", blocks);
        return response;
    }
}