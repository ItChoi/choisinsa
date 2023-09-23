package com.mall.choisinsa.api.authority;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import core.dto.admin.request.authority.AdminAuthorityMenuInsertRequestDto;
import core.service.authority.admin.AdminAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/authorities")
public class AdminAuthorityController {

    private final AdminAuthorityService adminAuthorityService;

    @PostMapping("/{authorityId}/menus")
    public ResponseWrapper insertAuthorityMenu(@PathVariable Long authorityId,
                                               @RequestBody Collection<AdminAuthorityMenuInsertRequestDto> requestDtos) {
        adminAuthorityService.insertAuthorityMenus(authorityId, requestDtos);
        return ResponseWrapper.ok();
    }

}
