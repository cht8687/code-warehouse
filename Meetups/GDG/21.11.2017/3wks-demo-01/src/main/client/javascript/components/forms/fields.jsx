import React from 'react';
import { shape, string } from 'prop-types';
import { fieldInputPropTypes, fieldMetaPropTypes } from 'redux-form';
import ChipInput from 'material-ui-chip-input';

// eslint-disable-next-line import/prefer-default-export
export const MultiselectField = (props) => {
  const {
    input: { onChange, onBlur, ...input },
    meta: { touched, error },
    dataSourceConfig,
    ...rest
  } = props;

  const values = input.value || [];

  return (
    <ChipInput
      {...input}
      {...rest}
      value={values}
      onBlur={() => { onBlur(); }}
      onRequestAdd={(adding) => {
        onChange([...values, adding]);
      }}
      onRequestDelete={(deleting) => {
        const matcher = dataSourceConfig
          ? value => value[dataSourceConfig.value] !== deleting
          : value => value !== deleting;
        onChange(values.filter(matcher));
      }}
      errorText={touched && error}
      dataSourceConfig={dataSourceConfig}
    />
  );
};

MultiselectField.propTypes = {
  input: shape(fieldInputPropTypes).isRequired,
  meta: shape(fieldMetaPropTypes).isRequired,
  dataSourceConfig: shape({
    text: string.isRequired,
    value: string.isRequired,
  }),
};

MultiselectField.defaultProps = {
  dataSourceConfig: null,
};
