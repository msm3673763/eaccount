package com.ucsmy.ucas.quartz.tasklet;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/14

 * Contributors:
 *      - initial implementation
 */

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * 暂无描述
 *
 * @author ucs_masiming
 * @since 2017/9/14
 */
@Component("aliPayTasklet")
public class AliPayTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED;
    }
}
