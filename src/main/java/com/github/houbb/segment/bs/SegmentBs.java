package com.github.houbb.segment.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.segment.api.ISegment;
import com.github.houbb.segment.api.ISegmentContext;
import com.github.houbb.segment.api.ISegmentResult;
import com.github.houbb.segment.support.data.ISegmentData;
import com.github.houbb.segment.support.data.impl.SegmentDatas;
import com.github.houbb.segment.support.format.ISegmentFormat;
import com.github.houbb.segment.support.format.impl.SegmentFormats;
import com.github.houbb.segment.support.segment.impl.SegmentContext;
import com.github.houbb.segment.support.segment.impl.Segments;
import com.github.houbb.segment.support.segment.mode.ISegmentMode;
import com.github.houbb.segment.support.segment.mode.impl.SegmentModes;
import com.github.houbb.segment.support.segment.result.ISegmentResultHandler;
import com.github.houbb.segment.support.segment.result.impl.SegmentResultHandlers;
import com.github.houbb.segment.support.type.ISegmentWordType;
import com.github.houbb.segment.support.type.impl.WordTypes;

import java.util.List;

/**
 * 分词引导类
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public final class SegmentBs {

    /**
     * 分词实现
     * @since 0.0.1
     */
    private ISegment segment = Segments.defaults();

    /**
     * 分词数据来源
     * 1. 默认使用混合模式的字典(v1.0.3)
     * @since 0.0.3
     */
    private ISegmentData data = SegmentDatas.mixed();

    /**
     * 分词模式
     *
     * 1. 默认使用贪婪模式
     *
     * @since 0.0.5
     */
    private ISegmentMode mode = SegmentModes.search();

    /**
     * 词性信息实现
     * @since 0.0.7
     */
    private ISegmentWordType wordType = WordTypes.none();

    /**
     * 格式化信息
     * @since 0.0.9
     */
    private ISegmentFormat format = SegmentFormats.defaults();

    /**
     * 引导类
     * @since 0.0.1
     */
    private SegmentBs(){}

    /**
     * 创建一个新的实例
     * @return this
     * @since 0.0.1
     */
    public static SegmentBs newInstance() {
        return new SegmentBs();
    }

    /**
     * 指定启用词性
     * @param segmentWordType 指定词性实现方式
     * @return this
     * @since 0.0.7
     */
    public SegmentBs segmentWordType(final ISegmentWordType segmentWordType) {
        ArgUtil.notNull(segmentWordType, "segmentWordType");
        this.wordType = segmentWordType;
        return this;
    }

    /**
     * 指定分词的数据实现
     * @param segmentData 分词数据
     * @return this
     * @since 0.0.3
     */
    public SegmentBs segmentData(final ISegmentData segmentData) {
        ArgUtil.notNull(segmentData, "segmentData");
        this.data = segmentData;
        return this;
    }

    /**
     * 设置分词模式
     * @param segmentMode 分词模式
     * @return this
     * @since 0.0.5
     */
    public SegmentBs segmentMode(ISegmentMode segmentMode) {
        ArgUtil.notNull(segmentMode, "segmentMode");
        this.mode = segmentMode;
        return this;
    }

    public SegmentBs segmentFormat(final ISegmentFormat segmentFormat) {
        ArgUtil.notNull(segmentFormat, "segmentFormat");
        this.format = segmentFormat;
        return this;
    }

    /**
     * 直接执行分词
     * @param string 字符串
     * @return 结果
     * @since 0.0.1
     */
    public List<ISegmentResult> segment(final String string) {
        return segment(string, SegmentResultHandlers.common());
    }

    /**
     * 分词处理
     * @param string 原始字符串
     * @param handler 处理类
     * @param <R> 泛型
     * @return 处理后的结果
     * @since 0.0.4
     */
    public <R> R segment(final String string, final ISegmentResultHandler<R> handler) {
        final ISegmentContext context = buildContext();
        List<ISegmentResult> segmentResults = segment.segment(string, context);
        return handler.handler(segmentResults);
    }

    /**
     * 构建上下文
     * @return 上下文
     * @since 0.0.3
     */
    private ISegmentContext buildContext() {
        return SegmentContext.newInstance()
                .wordType(wordType)
                .data(data)
                .mode(mode)
                .format(format);
    }

}
