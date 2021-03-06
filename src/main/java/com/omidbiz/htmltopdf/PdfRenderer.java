package com.omidbiz.htmltopdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.commonmark.ext.gfm.tables.internal.TableBlockParser;
import org.commonmark.internal.renderer.NodeRendererMap;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.parser.Parser.Builder;
import org.commonmark.renderer.Renderer;

import com.lowagie.text.DocumentException;

/**
 * @author Omid Pourhadi
 *
 */
public class PdfRenderer implements Renderer, Parser.ParserExtension
{

    private File file;
    private final NodeRendererMap nodeRendererMap = new NodeRendererMap();
    private PdfHolder pdfHolder;

    public PdfRenderer(File file) throws URISyntaxException, DocumentException, IOException
    {
        this.file = file;
        pdfHolder = new PdfHolder(file);
        nodeRendererMap.add(new CorePdfNodeRenderer(pdfHolder));
    }

    @Override
    public void render(Node node, Appendable output)
    {
        nodeRendererMap.render(node);
    }

    @Override
    public String render(Node node)
    {
        try
        {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            render(node, writer);
        }
        catch (UnsupportedEncodingException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @Override
    public void extend(Builder parserBuilder)
    {
        parserBuilder.customBlockParserFactory(new TableBlockParser.Factory());
    }

}
