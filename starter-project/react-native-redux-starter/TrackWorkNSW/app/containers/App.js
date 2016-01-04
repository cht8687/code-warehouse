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

import allActions from '../actions';


export default class App extends Component {

  constructor (props) {
    super(props);
    this.state = {
      tab: null
    };
  }

  componentWillReceiveProps (props) {
    const { application } = props;
    this.setState({
      tab: application.tab
    })
  }

  render () {
    const { tab } = this.state;

    return (
      <View style={styles.container}>
        <Text>
          TrackWork View
        </Text>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1
  }
})

App.propTypes = {
  actions: PropTypes.object
}

export default connect(state => {
  return {
    application: state.application
  }
}, dispatch => {
  return {
    actions: bindActionCreators(allActions, dispatch)
  }
})(App)
