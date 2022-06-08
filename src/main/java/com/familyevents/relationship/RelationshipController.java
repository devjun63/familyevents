package com.familyevents.relationship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RelationshipController {

    @GetMapping("/add_relation")
    public String relationshipForm() {
        return "relationship/add_relation";
    }
}
