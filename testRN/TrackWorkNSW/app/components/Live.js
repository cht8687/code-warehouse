'use strict'

import React, {
  Component,
  StyleSheet,
  View,
  Text,
  TabBarIOS,
  TouchableHighlight,
  PropTypes,
  ListView,
  Image
} from 'react-native';
import colorLookupTable from '../utils/colorLookUp';
import { buildLiveDataList } from '../utils/dataSourceFactory';

export default class Live extends Component {

  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const { actions } = this.props;
    actions.fetchNews();
  }

  renderRow(rowData) {

    return (
      <View style={styles.listViewContainer}>
        <View style={[styles.listViewLeftBanner, this.bgcolor(rowData.lineName)]}>
          <Text></Text>
        </View>
        <View style={styles.listViewContent}>
          <Text style={styles.lineName}> {rowData.lineName} </Text>
          <Text style={styles.serviceStatus}> {rowData.ServiceStatus} </Text>
        </View>
      </View>
    )
  }

  bgcolor(lineName) {

    const cor = colorLookupTable[lineName];

    return {
      backgroundColor: cor
    }
  }

  render() {

    const data = this.props.newsData;
    let dataSource = buildLiveDataList(data);

    return (
      <View style={[styles.tabContent]}>
        <View style={styles.tabHeader}>
          <Text style={styles.tabText}> Live Track News </Text>
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
  listViewContainer: {
    flex: 1,
    flexDirection: 'row',
    backgroundColor: '#F5FCFF',
    borderColor: 'grey',
    borderWidth: 2,
    marginTop: 3
  },
  listViewLeftBanner: {
    flex: 1
  },
  listViewContent: {
    flex: 10,
    flexDirection: 'column'
  },
  lineName: {
    color: '#000',
    fontSize: 16,
    fontWeight: 'bold',
    marginTop: 2
  },
  serviceStatus: {
    color: '#000',
    fontSize: 13,
    position: 'relative',
    top: 0
  }
})

Live.propTypes = {
  actions: PropTypes.object,
  newsData: PropTypes.array
}
