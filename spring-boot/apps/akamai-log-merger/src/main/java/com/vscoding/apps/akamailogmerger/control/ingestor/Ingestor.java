package com.vscoding.apps.akamailogmerger.control.ingestor;

import com.azure.storage.blob.models.BlobItem;

import java.util.Date;
import java.util.List;

public interface Ingestor {

  void ingest(Date date, String logData, List<BlobItem> blobItems);
}
