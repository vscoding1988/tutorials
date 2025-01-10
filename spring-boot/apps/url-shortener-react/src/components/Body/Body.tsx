import React, {useEffect, useState} from 'react';
import {CreationForm} from "./CreationForm/CreationForm";
import {ControlBar} from "./ControlBar/ControlBar";
import {Table} from "./Table/Table";
import {UrlDTO, UrlRequestSizeEnum} from "../../api";
import {shortUrlApi} from "../../constants/api";
import {Pagination} from "./Pagination/Pagination";
import {CONTENT} from "../../constants/constants";
import styles from './Body.module.scss';
import {Analytics} from "./Analytics/Analytics";

export const Body = (): React.ReactElement => {

  const [urls, setUrls] = useState<Array<UrlDTO>>([]);
  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<UrlRequestSizeEnum>(UrlRequestSizeEnum._10);
  const [term, setTerm] = useState<string>('');
  const [hits, setHits] = useState<number>(0);
  const [openTab, setOpenTab] = useState<number>(0);

  useEffect(() => {
    updateItems();
  }, [term, page, size]);

  function updateItems() {
    shortUrlApi.findUrls({
      urlRequest: {
        page: page,
        size: size,
        term: term
      }
    }).then((response) => {
      setUrls(response.urls || []);
      setHits(response.count || 0);
    });
  }

  function onSearch(term: string) {
    setTerm(term);
    setPage(0);
  }

  /**
   * Returns the maximum page number based on the current hits and page size, the page is 1 based
   */
  function getMaxPage(): number {
    let pageSize = getPageSize();

    return hits % pageSize === 0 ? hits / pageSize : Math.floor(hits / pageSize) + 1;
  }

  /**
   * Returns the page size based on the current size state, probably should find a better way to do this
   */
  function getPageSize(): number {
    let pageSize = 10;

    if (size === UrlRequestSizeEnum._25) {
      pageSize = 25;
    } else if (size === UrlRequestSizeEnum._50) {
      pageSize = 50;
    }

    return pageSize;
  }

  /**
   * Returns a string that describes the items that are currently shown like "Showing 21 to 28 of 28 URLs"
   */
  function getItemsDescription(): string {
    let pageSize = getPageSize();
    let firstShownItem = page * pageSize + 1;
    let lastShownItem = Math.min((page + 1) * pageSize, hits);

    return CONTENT.TABLE.PAGINATION.PAGE_DESCRIPTION(
            firstShownItem,
            lastShownItem,
            hits)
  }

  return (
          <main>
            <CreationForm onCreation={updateItems}/>
            <div className={styles.tabContainer}>
              <div className={styles.tabHeader}>
                <div className={styles.tabHeaderWrapper + " contentWidth"}>
                  <div className={styles.tabHeaderItem + " " + (openTab == 0 ? styles.active : "")}
                       onClick={() => setOpenTab(0)}>
                    <span>URLs</span>
                  </div>
                  <div className={styles.tabHeaderItem + " " + (openTab == 1 ? styles.active : "")}
                       onClick={() => setOpenTab(1)}>
                    <span>Analytics</span>
                  </div>
                </div>
              </div>

              <div className={styles.tabContainerWrapper + " contentWidth"}>
                <div className={styles.tabItem + " " + (openTab == 0 ? styles.active : "")}>
                  <ControlBar onSearch={onSearch} onSizeChange={setSize} term={term}/>
                  <Table urls={urls}/>
                  <div className={styles.paginationWrapper + " contentWidth"}>
                    <span className={styles.hits}>{getItemsDescription()}</span>
                    <Pagination currentPage={page} maxPages={getMaxPage()} onPageChange={setPage}/>
                  </div>
                </div>
                <div className={styles.tabItem + " " + (openTab == 1 ? styles.active : "")}>
                  <Analytics/>
                </div>
              </div>
            </div>
          </main>
  )
}
