
import React, {
  ListView
} from 'react-native';

export function buildLiveDataList(data) {
  let dataSource = new ListView.DataSource({
    rowHasChanged: (r1, r2) => r1 !== r2
  });
  dataSource = dataSource.cloneWithRows(data);
  return dataSource;
}