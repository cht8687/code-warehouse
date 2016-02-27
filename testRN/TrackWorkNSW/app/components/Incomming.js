'use strict'

import React, {
  Component,
  StyleSheet,
  View,
  Text,
  TabBarIOS,
  TouchableHighlight,
  PropTypes,
  ListView
} from 'react-native';
import colorLookupTable from '../utils/colorLookUp';
import { buildLiveDataList } from '../utils/dataSourceFactory';

export default class Incomming extends Component {

  constructor(props) {
    super(props);

  }

  componentWillMount() {
    const { actions } = this.props;
    actions.fetchIncommingNews();
  }

  bgcolor(lineName) {

    const cor = colorLookupTable[lineName];

    return {
      backgroundColor: cor
    }
  }

  renderIncommingRow(rowData) {

    return (
      <View style={styles.listViewContainer}>
        <Text style={styles.timeInfo}>{rowData.time}</Text>
        <Text style={styles.incomingContent}>{rowData.content}</Text>
      </View>
    )
  }

  renderRow(rowData) {

    let dataSource = buildLiveDataList(rowData.Content);

    return (
      <View style={styles.listViewContainer}>
        <View style={[styles.listViewLeftBanner, this.bgcolor(rowData.lineName)]}>
          <Text style={styles.lineName}> {rowData.lineName} </Text>
        </View>
        <View style={styles.listHeader}>
        </View>
        <View style={styles.listViewContent}>
          <ListView
            dataSource={dataSource}
            renderRow={this.renderIncommingRow.bind(this)}
          />
        </View>
      </View>
    )
  }

  render() {

    const data = this.props.inCommingNewsData;
    let dataSource = buildLiveDataList(data);

    return (
      <View style={[styles.tabContent]}>
        <View style={styles.tabHeader}>
          <Text style={styles.tabText}> Comming (next 2 weeks) </Text>
        </View>
        <ListView
          dataSource={dataSource} 
          renderRow={this.renderRow.bind(this)}
          style={styles.listView}
        />
      </View>
    )
  }
}

const styles = StyleSheet.create({
  tabContent: {
    flex: 1
  },
  tabHeader: {
    alignItems: 'center',
    marginTop: 15
  },
  tabText: {

  },
  tabTint: {
    color: '#1B82C5'
  },
  barTint: {
    color: 'white'
  },
  listView: {
    flex: 1,
    marginTop: 20
  },
  listHeader: {
    flex: 1,
    flexDirection: 'row',
    backgroundColor: '#F5FCFF',
    borderColor: 'grey'
  },
  listViewLeftBanner: {
    flex: 1
  },
  listViewContent: {
    flex: 10,
    flexDirection: 'column'
  },
  lineName: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
    marginTop: 2
  },
  serviceStatus: {
    color: '#000',
    fontSize: 13,
    position: 'relative',
    top: 0
  },
  timeInfo: {
    fontWeight: 'bold',
    backgroundColor: '#ccc',
    padding: 5
  },
  incomingContent: {
    padding: 15,
    paddingRight: 20
  }
})

Incomming.propTypes = {
  actions: PropTypes.object,
  inCommingNewsData: PropTypes.array
}
