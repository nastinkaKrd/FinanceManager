import { CategoryDto } from './category-dto';

describe('Category', () => {
  it('should create an instance', () => {
    const title = 'sampleTitle';
    const description = 'sampleDescription';
    expect(new CategoryDto(title, description)).toBeTruthy();
  });
});
