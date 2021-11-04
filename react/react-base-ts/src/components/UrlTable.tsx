import React, {useEffect, useState} from "react";
import {CTable, CTableBody, CTableHead, CTableHeaderCell, CTableRow} from "@coreui/react";
import UrlTableRow, {IUrlModel} from "./UrlTableRow";

const UrlTable = () => {
  const ajaxUrl: string = "http://localhost:8080/api/urls";
  const [urls, setUrls] = useState<Array<IUrlModel>>([]);

  useEffect(() => {
    updateUrls();
  });

  const updateUrls = function () {
    fetch(ajaxUrl).then(function (response) {
      return response.json();
    }).then(function (j) {
      setUrls(j);
    });
  }

  return (
          <div className="table-wrapper">
            <h2>Overview</h2>
            <CTable striped>
              <CTableHead>
                <CTableRow>
                  <CTableHeaderCell scope="col">Url</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Short Url</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Total clicks</CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                {
                  urls.map(model => {
                    return <UrlTableRow data={model}/>;
                  })
                }
              </CTableBody>
            </CTable>
          </div>
  );
}
export default UrlTable;
