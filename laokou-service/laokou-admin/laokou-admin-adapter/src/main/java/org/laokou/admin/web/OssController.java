/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.laokou.admin.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.laokou.common.i18n.dto.Result;
import org.laokou.common.trace.annotation.TraceLog;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author laokou
 */
@RestController
@Tag(name = "OssController", description = "存储")
@RequiredArgsConstructor
public class OssController {

	@TraceLog
	@PostMapping("v1/oss/list")
	@Operation(summary = "查询", description = "查询")
	// @PreAuthorize("hasAuthority('oss:list')")
	public Result<?> list() {
		return Result.of(null);
	}

	@TraceLog
	@PostMapping("v1/oss/upload/{md5}")
	@Operation(summary = "上传", description = "上传")
	public Result<?> upload(@RequestPart("file") MultipartFile file, @PathVariable("md5") String md5) {
		return Result.of(null);
	}

	@TraceLog
	@PostMapping("v1/oss")
	@Operation(summary = "新增", description = "新增")
	// @OperateLog(module = "对象存储", name = "新增")
	// @PreAuthorize("hasAuthority('oss:insert')")
	public Result<Boolean> insert() {
		return Result.of(null);
	}

	@TraceLog
	@GetMapping("v1/oss/use/{id}")
	@Operation(summary = "启用", description = "启用")
	// @OperateLog(module = "对象存储", name = "启用")
	// @PreAuthorize("hasAuthority('oss:use')")
	public Result<Boolean> use(@PathVariable("id") Long id) {
		return Result.of(null);
	}

	@TraceLog
	@GetMapping("v1/oss/{id}")
	@Operation(summary = "查看", description = "查看")
	// @DataCache(name = "oss", key = "#id")
	public Result<?> get(@PathVariable("id") Long id) {
		return Result.of(null);
	}

	@TraceLog
	@PutMapping("v1/oss")
	@Operation(summary = "修改", description = "修改")
	// @OperateLog(module = "对象存储", name = "修改")
	// @PreAuthorize("hasAuthority('oss:update')")
	// @DataCache(name = "oss", key = "#dto.id", type = CacheEnum.DEL)
	public Result<Boolean> update() {
		return Result.of(null);
	}

	@TraceLog
	@DeleteMapping("v1/oss/{id}")
	@Operation(summary = "删除", description = "删除")
	// @OperateLog(module = "对象存储", name = "删除")
	// @PreAuthorize("hasAuthority('oss:delete')")
	// @DataCache(name = "oss", key = "#id", type = CacheEnum.DEL)
	public Result<Boolean> delete(@PathVariable("id") Long id) {
		return Result.of(null);
	}

}