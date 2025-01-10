import React from 'react';
import styles from './Pagination.module.scss';
import {CONTENT} from "../../../constants/constants";

interface PaginationProps {
  onPageChange: Function;
  // page index that we are on right now (0 based)
  currentPage: number;
  // defines the count of pages, not the index (1 based)
  maxPages: number;
}

export const Pagination = ({onPageChange, currentPage, maxPages}: PaginationProps): React.ReactElement => {

  function handlePageChange(pageNumber: number) {
    if (pageNumber < 0 || pageNumber >= maxPages || pageNumber === currentPage) {
      return;
    }
    onPageChange(pageNumber);
  }

  function getPages(): Array<number> {
    let rightBorder = Math.min(currentPage + 5, maxPages);
    let leftBorder = Math.max(currentPage - 5, 0);

    let pages = [];

    for (let i = leftBorder; i < rightBorder; i++) {
      pages.push(i);
    }

    return pages;
  }

  return (
          <div className={styles.pagination+(getPages().length==1?' hidden':'')}>
            <div className={styles.paginationSelector}>
              <div className={currentPage === 0 ? styles.disabled : ''}
                   onClick={() => handlePageChange(currentPage - 1)}>{CONTENT.TABLE.PAGINATION.PREVIOUS}</div>
              {
                getPages().map((page) =>
                        <div key={page}
                             onClick={() => handlePageChange(page)}
                             className={currentPage === page ? styles.active : ''}>
                          {page + 1}
                        </div>
                )
              }
              {/*currentPage is 0 based, maxPages is 1 based, so we need -1*/}
              <div className={currentPage === maxPages - 1 ? styles.disabled : ''}
                   onClick={() => handlePageChange(currentPage + 1)}>{CONTENT.TABLE.PAGINATION.NEXT}</div>
            </div>
          </div>
  )
}
