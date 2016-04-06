'use strict'

import React, {
  Component,
  View,
  StyleSheet,
  PropTypes,
  Text
} from 'react-native';
import { connect } from 'react-redux/native';
import { bindActionCreators } from 'redux';
import Home from '../components/Home';
import * as allActions from '../actions/home';

export default class App extends Component {

  constructor (props) {
    super(props);
  }

  render() {
    const { currentDirection, tapTextShow, actions } = this.props;
    debugger;
    return (
      <View>
        <Home currentDirection={currentDirection} tapTextShow={tapTextShow} actions={actions} />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1
  }
})

App.propTypes = {
  currentDirection: PropTypes.string,
  tapTextShow: PropTypes.bool,
  actions: PropTypes.object
}

function mapStateToProps(state) {
  return {
    currentDirection: state.home.currentDirection,
    tapTextShow: state.home.tapTextShow
  };
}

function mapDispatchToProps(dispatch) {
  debugger;
  return {
    actions: bindActionCreators(allActions, dispatch)
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App)


// export default connect(state => ({
//      tapTextShow: state.home.tapTextShow,
//      currentDirection: state.home.currentDirection
//   }),
//   (dispatch) => ({
//     actions: bindActionCreators(allActions, dispatch)
//   })
// )(App);
