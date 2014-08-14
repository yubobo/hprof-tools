package com.badoo.hprof.library.heap;

import com.google.common.io.CountingInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Erik Andre on 16/07/2014.
 */
public class HeapDumpReader {

    private final CountingInputStream in;
    private final HeapDumpProcessor processor;
    private final int length;

    /**
     * Creates a reader to process a number of heap dump records (See HeapTag for types).
     *
     * @param in        InputStream to read the heap records from
     * @param length    Total length in bytes of the heap records to process
     * @param processor A callback interface that is invoked when a new record is encountered
     * @throws IOException
     */
    public HeapDumpReader(InputStream in, int length, HeapDumpProcessor processor) throws IOException {
        this.in = new CountingInputStream(in);
        this.processor = processor;
        this.length = length;
    }

    public boolean hasNext() throws IOException {
        return in.getCount() < length;
    }

    public void next() throws IOException {
        int tag = in.read();
        processor.onHeapRecord(tag, in);
    }
}