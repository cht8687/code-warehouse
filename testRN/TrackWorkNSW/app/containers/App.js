'use strict'

import React, {
  Component,
  View,
  StyleSheet,
  PropTypes,
  Text
} from 'react-native'
import { connect } from 'react-redux/native'
import { bindActionCreators } from 'redux';
import Tabbar from '../components/Tabbar';
import allActions from '../actions';

export default class App extends Component {

  constructor (props) {
    super(props);
  }

  componentWillReceiveProps (nextProps) {
    
  }

  render() {
    const { tabName, actions, newsData, inCommingNewsData } = this.props;

    return (
      <Tabbar
        tabName={tabName}
        actions={actions}
        newsData={newsData}
        inCommingNewsData={inCommingNewsData}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1
  }
})

App.propTypes = {
  tabName: PropTypes.string,
  actions: PropTypes.object,
  newsData: PropTypes.array,
  inCommingNewsData: PropTypes.array
}

function mapStateToProps(state) {
  return {
    tabName: state.tab.tabName,
    newsData: state.news.newsData,
    inCommingNewsData: state.news.inCommingNewsData
  };
}

function mapDispatchToProps(dispatch) {
  return {
    actions: bindActionCreators(allActions, dispatch)
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App)
