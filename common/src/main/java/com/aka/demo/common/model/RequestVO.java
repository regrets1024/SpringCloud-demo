package com.aka.demo.common.model;

import com.aka.demo.common.service.Add;
import com.aka.demo.common.service.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestVO {

    @NotBlank(message = "姓名不能为空", groups = Update.class)
    @Length(min = 2, max = 4, message = "姓名长度需在2~4之间")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Range(min = 1, max = 150, message = "年龄需在1~150之间")
    private Integer age;

    @NotEmpty(message = "列表不能为空", groups = Add.class)
    @Size(min = 2, message = "列表长度需大于等于2")
    private List<String> list;

}
