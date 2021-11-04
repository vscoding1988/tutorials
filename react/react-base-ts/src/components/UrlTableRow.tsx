import React from "react";
import {CTableDataCell, CTableRow} from "@coreui/react";

interface IUrlTableRow{
  data:IUrlModel;
}
/**
 * Representation of UrlDTO
 */
export interface IUrlModel {
  shortUrl: string;
  targetUrl: string;
  description: string;
  calls: number;
}
const UrlTableRow = (prop:IUrlTableRow) =>{
  const data = prop.data;

    return (
          <CTableRow key={data.shortUrl}>
            <CTableDataCell title={data.targetUrl}>{data.description || data.targetUrl}</CTableDataCell>
            <CTableDataCell>{data.shortUrl}</CTableDataCell>
            <CTableDataCell>{data.calls}</CTableDataCell>
          </CTableRow>
    );
}
export default UrlTableRow;
