package org.smartparam.serializer.entries;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartparam.engine.model.Level;
import org.smartparam.engine.model.Parameter;
import org.smartparam.engine.model.ParameterEntry;
import org.smartparam.serializer.exception.SmartParamSerializationException;
import org.smartparam.serializer.SerializationConfig;
import org.supercsv.io.CsvListWriter;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 */
public class CsvParameterEntrySerializer implements ParameterEntrySerializer {

    private static final Logger logger = LoggerFactory.getLogger(CsvParameterEntrySerializer.class);

    private static final int PARAMETER_ENTRY_BATCH_SIZE = 500;

    @Override
    public void serialize(SerializationConfig config, Writer writer, Parameter parameter, ParameterEntryBatchLoader parameterEntryLoader) throws SmartParamSerializationException {
        CsvListWriter csvWriter = new CsvListWriter(writer, CsvPreferenceBuilder.csvPreference(config));

        try {
            long startTime = System.currentTimeMillis();
            logger.debug("started parameter entries serialization at {}", startTime);
            csvWriter.write(extractHeader(parameter));

            int counter = 0;
            while (parameterEntryLoader.hasMore()) {
                for (ParameterEntry entry : parameterEntryLoader.nextBatch(PARAMETER_ENTRY_BATCH_SIZE)) {
                    csvWriter.write(entry.getLevels());
                    counter++;
                }
            }

            long endTime = System.currentTimeMillis();
            logger.debug("serializing {} parameter entries took {}", counter, endTime - startTime);
        } catch (IOException exception) {
            throw new SmartParamSerializationException("serialization error", exception);
        } finally {
            closeWriter(csvWriter);
        }
    }

    private List<String> extractHeader(Parameter parameter) {
        List<String> header = new ArrayList<String>(parameter.getLevels().size());
        for (Level level : parameter.getLevels()) {
            header.add(level.getName());
        }

        return header;
    }

    private void closeWriter(CsvListWriter writer) throws SmartParamSerializationException {
        try {
            writer.close();
        } catch (IOException exception) {
            throw new SmartParamSerializationException("error while closing writer stream", exception);
        }
    }
}
