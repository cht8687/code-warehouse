import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { submit } from 'redux-form';
import { Dialog, FlatButton } from 'material-ui';

class FormDialog extends Component {
  static propTypes = {
    title: PropTypes.string.isRequired,
    formComponent: PropTypes.oneOfType([PropTypes.func, PropTypes.object]).isRequired,
    formName: PropTypes.string.isRequired,
    dispatch: PropTypes.func.isRequired,
    onCancel: PropTypes.func.isRequired,
    onSubmit: PropTypes.func.isRequired,
    onDelete: PropTypes.func,
    open: PropTypes.bool,
    cancelButtonText: PropTypes.string,
    submitButtonText: PropTypes.string,
    deleteButtonText: PropTypes.string,
    initialValues: PropTypes.object,
  };

  static defaultProps = {
    open: false,
    cancelButtonText: 'Cancel',
    submitButtonText: 'Submit',
    deleteButtonText: 'Delete',
    onDelete: null,
    initialValues: null,
  };

  handleSubmit = () => {
    const { dispatch, formName } = this.props;

    dispatch(submit(formName));
  };

  handleDelete = () => {
    const { onDelete, initialValues } = this.props;

    return onDelete && onDelete(initialValues);
  };

  render() {
    const {
      title,
      open,
      cancelButtonText,
      submitButtonText,
      deleteButtonText,
      onCancel,
      onSubmit,
      onDelete,
      formComponent: FormComponent,
      ...rest
    } = this.props;

    const actions = [
      <FlatButton label={cancelButtonText} onTouchTap={onCancel} />,
      onDelete && <FlatButton label={deleteButtonText} secondary onTouchTap={this.handleDelete} />,
      <FlatButton
        label={submitButtonText}
        primary
        keyboardFocused
        onTouchTap={this.handleSubmit}
      />,
    ];

    return (
      <Dialog title={title} open={open} onRequestClose={onCancel} actions={actions}>
        <FormComponent onSubmit={onSubmit} {...rest} />
      </Dialog>
    );
  }
}

export default connect()(FormDialog);
