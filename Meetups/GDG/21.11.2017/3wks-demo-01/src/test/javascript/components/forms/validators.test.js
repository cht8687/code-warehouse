import * as validators from '../../../../../src/main/client/javascript/components/forms/validators';

describe('Validating', () => {
  describe('required', () => {
    const required = validators.required();

    test('accepts a non-blank value', () => {
      const message = required('  test ');

      expect(message).toBeFalsy();
    });

    test('can override the default message', () => {
      const customRequired = validators.required('foo is required');
      const message = customRequired(null);

      expect(message).toBe('foo is required');
    });

    test('rejects a blank value', () => {
      const message = required('  ');

      expect(message).toBe('is required');
    });

    test('rejects a empty value', () => {
      const message = required('');

      expect(message).toBe('is required');
    });

    test('rejects a null value', () => {
      const message = required(null);

      expect(message).toBe('is required');
    });

    test('rejects a undefined value', () => {
      const message = required(undefined);

      expect(message).toBe('is required');
    });
  });

  describe('email', () => {
    const email = validators.email();

    test('accepts undefined value', () => {
      const message = email(undefined);

      expect(message).toBeFalsy();
    });

    test('accepts null value', () => {
      const message = email(null);

      expect(message).toBeFalsy();
    });

    test('accepts empty value', () => {
      const message = email('');

      expect(message).toBeFalsy();
    });

    test('accepts a valid email', () => {
      const message = email('foo@example.org');
      expect(message).toBeFalsy();
    });

    test('can override the default message', () => {
      const customEmail = validators.email('email address is invalid');
      const message = customEmail('invalid');

      expect(message).toBe('email address is invalid');
    });

    test('rejects a invalid email', () => {
      const message = email('a@b');
      expect(message).toBe('is invalid');
    });

    test('rejects a blank email', () => {
      const message = email(' ');
      expect(message).toBe('is invalid');
    });
  });
});
