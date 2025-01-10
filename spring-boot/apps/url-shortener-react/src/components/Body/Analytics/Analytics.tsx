import React from 'react';
import styles from './Analytics.module.scss';
import {Line, LineChart, XAxis, YAxis} from "recharts";

export const Analytics = (): React.ReactElement => {
  const data = [{date: 'Page A', uv: 400, pv: 2400, amt: 2400},{date: 'Page B', uv: 300, pv: 2400, amt: 2400}];

  return (
          <div>
            <h1>Analytics</h1>
            <LineChart data={data} width={400} height={100}>
              <Line type="monotone" dataKey="uv" stroke="#8884d8" />
              <YAxis />
              <XAxis dataKey="date" />
            </LineChart>
          </div>
  )
}
