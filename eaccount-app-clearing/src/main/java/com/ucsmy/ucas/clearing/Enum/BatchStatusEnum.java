/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ucsmy.ucas.clearing.Enum;

/**
 * 对账状态枚举
 *
 * @author：ucs_masiming
 */
public enum BatchStatusEnum {

	SUCCESS("对账操作成功"),

	FAIL("对账操作失败"),

	ERROR("银行返回错误信息"),

	NOBILL("银行没有订单信息");

	private String desc;

	private BatchStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static BatchStatusEnum getEnum(String name) {
		BatchStatusEnum[] arry = BatchStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

}
