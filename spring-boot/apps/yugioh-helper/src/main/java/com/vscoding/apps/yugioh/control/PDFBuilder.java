package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.PDFCreationRequest;
import com.vscoding.apps.yugioh.control.bean.ImagePosition;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Slf4j
@Service
public class PDFBuilder {
  private static final float POINTS_PER_MM = 2.8346457F;
  private static final PDRectangle PAGE_SIZE = PDRectangle.A4;

  /**
   * Builds a PDF from the given request.
   *
   * @param request the request
   * @return the PDF as byte array
   */
  public ByteArrayOutputStream buildPDF(PDFCreationRequest request) {
    var cardsPerRow = cardsPerRow(request);
    var cardsPerPage = cardsPerColumn(request) * cardsPerRow;

    try (var document = new PDDocument()) {
      PDPage page;
      PDPageContentStream contentStream = null;

      for (int i = 0; i < request.images().size(); i++) {
        if (i % cardsPerPage == 0) {
          if (contentStream != null) {
            contentStream.close();
          }

          page = new PDPage(PAGE_SIZE);
          document.addPage(page);
          contentStream = new PDPageContentStream(document, page);
        }

        var image = request.images().get(i);
        var imagePosition = getImagePosition(i % cardsPerPage, request, cardsPerRow);

        var pdfImage = PDImageXObject.createFromByteArray(document, image.getBytes(), image.getName());
        contentStream.drawImage(pdfImage, imagePosition.getX(), imagePosition.getY(), imagePosition.getWidth(), imagePosition.getHeight());
      }

      if (contentStream != null) {
        contentStream.close();
      }

      var stream = new ByteArrayOutputStream();
      document.save(stream);

      return stream;
    } catch (Exception e) {
      log.error("Could not generate PDF", e);
    }

    return null;
  }

  /**
   * Calculates the position of the image on the page.
   *
   * @param index       the index of the image
   * @param config      the configuration
   * @param cardsPerRow the number of cards that fit in a row
   * @return the position of the image
   */
  private ImagePosition getImagePosition(int index, PDFCreationRequest config, int cardsPerRow) {
    var width = config.cardWidthMM() * POINTS_PER_MM;
    var height = config.cardHeightMM() * POINTS_PER_MM;
    var currentRow = index / cardsPerRow + 1;

    var x = config.marginLeft() + (index % cardsPerRow) * (width + config.horizontalSpacing());
    var y = PAGE_SIZE.getUpperRightY() - (config.marginTop() + currentRow * (height + config.verticalSpacing()));

    return new ImagePosition(x, y, width, height);
  }

  /**
   * Calculates the number of cards that fit in a row.
   *
   * @param config the configuration
   * @return the number of cards that fit in a row
   */
  private int cardsPerRow(PDFCreationRequest config) {
    var width = config.cardWidthMM() * POINTS_PER_MM;
    return (int) ((PAGE_SIZE.getWidth() - 2 * config.marginLeft()) / (width + config.horizontalSpacing()));
  }

  /**
   * Calculates the number of cards that fit in a column.
   *
   * @param config the configuration
   * @return the number of cards that fit in a column
   */
  private int cardsPerColumn(PDFCreationRequest config) {
    var height = config.cardHeightMM() * POINTS_PER_MM;
    return (int) ((PAGE_SIZE.getHeight() - 2 * config.marginTop()) / (height + config.verticalSpacing()));
  }
}
