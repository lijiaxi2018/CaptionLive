import React, { useState, useMemo, useEffect } from 'react';
import { Table } from 'rsuite';

/** 
 * for guide of using rsuite/Table, please refer 
 * https://rsuitejs.com/components/table 
*/
const useSortedData = (data, config = null) => {
    const [sortConfig, setSortConfig] = useState(config);

    const sortedData = useMemo(() => {
        let sortableData = [...data];
        if (sortConfig !== null) {
          sortableData.sort((a, b) => {
            if (a[sortConfig.key] < b[sortConfig.key]) {
              return sortConfig.direction === "ascending" ? -1 : 1;
            }
            if (a[sortConfig.key] > b[sortConfig.key]) {
              return sortConfig.direction === "ascending" ? 1 : -1;
            }
            return 0;
          });
        }
        return sortableData;
      }, [data, sortConfig]);

      const requestSort = key => {
        let direction = "ascending";
        if (sortConfig && 
          sortConfig.key === key && 
          sortConfig.direction === 'ascending') {
              direction = 'descending';
          }
        setSortConfig({ key, direction });
      };
    
      return { data:sortedData, requestSort, sortConfig};
}

const GlossariesTable = ({glossaries=[], keyword}) => {
    const { Column, HeaderCell, Cell } = Table; // componets of rsuite table
    
    // const { data, requestSort, sortConfig } = useSortedData(glossaries);
    
    return (
        <div className='glossary-table'>
            <Table
                wordWrap="break-word"
                height={400}
                data={glossaries.filter(term => 
                        term.source.toLowerCase().includes(keyword.toLowerCase()) 
                          || term.romanization.toLowerCase().includes(keyword.toLowerCase())
                          || term.term.toLowerCase().includes(keyword.toLowerCase())
                          || term.explanation.toLowerCase().includes(keyword.toLowerCase())
                          || term.category.toLowerCase().includes(keyword.toLowerCase())
                          || term.remark.toLowerCase().includes(keyword.toLowerCase())
                    )
                }
                // onSortColumn={(sortCol, sortType) => {console.log(sortCol, sortType)}}
            >
              <Column width={180} align='center' fix>
                <HeaderCell>出自</HeaderCell>
                <Cell dataKey='source' />
              </Column>
              <Column width={230} align='center' fix>
                <HeaderCell>罗马字</HeaderCell>
                <Cell dataKey='romanization' />
              </Column>
              <Column width={230} align='center' fix>
                <HeaderCell>原文</HeaderCell>
                <Cell dataKey='term' />
              </Column>
              <Column width={230} align='center' fix>
                <HeaderCell>释义</HeaderCell>
                <Cell dataKey='explanation' />
              </Column>
              <Column width={150} align='center' fix>
                <HeaderCell>备注</HeaderCell>
                <Cell dataKey='remark' />
              </Column>
              <Column width={180} align='center' fix>
                <HeaderCell>分类</HeaderCell>
                <Cell dataKey='category' />
              </Column>
            </Table>
        </div>
    )
}

export default GlossariesTable;