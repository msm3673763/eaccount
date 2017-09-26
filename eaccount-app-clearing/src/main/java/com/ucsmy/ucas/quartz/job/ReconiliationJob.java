package com.ucsmy.ucas.quartz.job;

import com.ucsmy.core.utils.UUIDGenerator;
import com.ucsmy.ucas.clearing.entity.AliPayBill;
import com.ucsmy.ucas.clearing.entity.EcCheckBatch;
import com.ucsmy.ucas.clearing.service.EcCheckBatchService;
import com.ucsmy.ucas.clearing.service.TradeService;
import com.ucsmy.ucas.clearing.utils.DateUtil;
import com.ucsmy.ucas.quartz.processor.AliPayItemProcessor;
import com.ucsmy.ucas.quartz.processor.AliPayItemWriter;
import com.ucsmy.ucas.quartz.tasklet.AliPayTasklet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 支付宝对账定时任务
 *
 * @author ucs_masiming
 * @since 2017/9/13
 */
public class ReconiliationJob extends BaseBatchJob {

    private static Logger logger = LoggerFactory.getLogger(ReconiliationJob.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private AliPayTasklet aliPayTasklet;
    @Autowired
    private EcCheckBatchService ecCheckBatchService;
    @Autowired
    private AliPayItemProcessor aliPayItemProcessor;
    @Autowired
    private AliPayItemWriter aliPayItemWriter;
    @Autowired
    private TradeService tradeService;
    private File file;

    protected Step aliPayStep() throws Exception {
        // 获取需要对账的对账单时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date billDate = DateUtil.addDay(new Date(), -1);
        String billDateStr = sdf.format(billDate);
        Boolean flag = ecCheckBatchService.isChecked("alipay", billDateStr);
        if (flag) {
            logger.info("账单日[" + sdf.format(billDate) + "],支付宝已经对过账，不能再次发起自动对账。");
            return this.stepBuilderFactory.get("aliPayStep").tasklet(aliPayTasklet).build();
        } else {
            //创建对账批次
            EcCheckBatch batch = new EcCheckBatch();
            batch.setCreater("sys");
            batch.setCreateTime(new Date());
            batch.setBillDate(billDateStr);
            batch.setBillType("aliPay");
            String certUUID = UUIDGenerator.generate();
            batch.setId(certUUID);//流水号
            batch.setAccountCheckBatchNo(certUUID);//批次号

            //下载对账文件
            file = tradeService.downloadTradeFile(billDate);
            if (file == null) {
                return this.stepBuilderFactory.get("aliPayStep").tasklet(aliPayTasklet).build();
            }

            return this.stepBuilderFactory.get("aliPayStep").<AliPayBill, AliPayBill> chunk(2)
                    .reader(reader()).processor(aliPayItemProcessor).writer(aliPayItemWriter)
                    .build();
        }
    }

    /**
     * @Description: 读取支付宝账单excel文件
     */
    @Bean
    public FlatFileItemReader<AliPayBill> reader() throws MalformedURLException {
        FlatFileItemReader<AliPayBill> reader = new FlatFileItemReader<>();
        //reader.setResource(new UrlResource("file:\\D:\\20881021714158790156_20170912_业务明细.csv"));
        reader.setResource(new UrlResource("file:\\" + file.getPath()));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<AliPayBill>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "tradeNo", "orderNo", "businessType", "productName", "createTime", "endTime",
                        "storeNo", "storeName", "operator", "clientNo", "counterpartAccount", "orderAmount",
                        "merchantIncome", "alipayRedPacket", "jfTreasure", "alipayOffer", "merchantOffer", "volume",
                        "volumeName", "merchantRed", "cardPayAmount", "refundBatchNo", "serviceCharge", "subRun",
                        "remark"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<AliPayBill>() {{
                setTargetType(AliPayBill.class);
            }});
        }});
        return reader;
    }

    @Override
    protected Job getJob() throws Exception {
        return this.jobBuilderFactory.get("aliPayJob").flow(aliPayStep()).end().build();
    }
}
